#!/usr/bin/env python3

# Copyright (c) 2018 Anki, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License in the file LICENSE.txt or at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

'''
Make Cozmo turn toward a face and send the requests backend service if it
find out the visitor name, and say the welcome words from the service response.

Please install cozmo sdk first.
'''

import asyncio
import time
import requests

import cozmo
from cozmo.objects import LightCube1Id, LightCube2Id, LightCube3Id

class BlinkyCube(cozmo.objects.LightCube):
    '''Subclass LightCube and add a light-chaser effect.'''
    def __init__(self, *a, **kw):
        super().__init__(*a, **kw)
        self._chaser = None

    def start_light_chaser(self):
        '''Cycles the lights around the cube with 1 corner lit up green,
        changing to the next corner every 0.1 seconds.
        '''
        if self._chaser:
            raise ValueError("Light chaser already running")
        async def _chaser():
            while True:
                for i in range(4):
                    cols = [cozmo.lights.off_light] * 4
                    cols[i] = cozmo.lights.green_light
                    self.set_light_corners(*cols)
                    await asyncio.sleep(0.1, loop=self._loop)
        self._chaser = asyncio.ensure_future(_chaser(), loop=self._loop)

    def stop_light_chaser(self):
        if self._chaser:
            self._chaser.cancel()
            self._chaser = None

# Make sure World knows how to instantiate the subclass
cozmo.world.World.light_cube_factory = BlinkyCube

def follow_faces(robot: cozmo.robot.Robot):
    '''The core of the follow_faces program'''

    # Move lift down and tilt the head up
    robot.move_lift(-3)
    robot.set_head_angle(cozmo.robot.MAX_HEAD_ANGLE).wait_for_completed()

    face_to_follow = None

    print("Press CTRL-C to quit")
    while True:
        turn_action = None
        if face_to_follow:
            # start turning towards the face
            turn_action = robot.turn_towards_face(face_to_follow)

        if not (face_to_follow and face_to_follow.is_visible):
            # find a visible face, timeout if nothing found after a short while
            try:
                face_to_follow = robot.world.wait_for_observed_face(timeout=30)
            except asyncio.TimeoutError:
                print("Didn't find a face - exiting!")
                return

        if turn_action:
            # Complete the turn action if one was in progress
            turn_action.wait_for_completed()

        # Just show the face_to_follow name
        print("fact_to_follow:", face_to_follow.name)
        if face_to_follow.name:
            # Send the request to backend service, you may need to update the host address
            URL = "http://localhost:9080/visitor/" + face_to_follow.name
            r = requests.get(url = URL)
            if (r.status_code == 200):
                message = r.text
            else:
                message = "System error!"
            # We need to wait robot finish it job
            robot.say_text(message).wait_for_completed()
            # Do something with the Response
            print("light cubes ")
            light_cubes(robot)
        # Wait for two seconds
        time.sleep(2)


def light_cubes(robot: cozmo.robot.Robot):
    cube1 = robot.world.get_light_cube(LightCube1Id)  # looks like a paperclip
    cube2 = robot.world.get_light_cube(LightCube2Id)  # looks like a lamp / heart
    cube3 = robot.world.get_light_cube(LightCube3Id)  # looks like the letters 'ab' over 'T'

    cubes = [cube1, cube2, cube3]

    if cube1 is None or cube2 is None or cube3 is None:
        cozmo.logger.warning("Cozmo is not connected to a LightCube1 cube - check the battery.")
        return

    for cube in cubes:
        cube.start_light_chaser()

    # Keep the lights on for 10 seconds until the program exits
    time.sleep(2)
    for cube in cubes:
        cube.stop_light_chaser()
        cube.set_lights_off()

cozmo.run_program(follow_faces, use_viewer=True, force_viewer_on_top=True)
