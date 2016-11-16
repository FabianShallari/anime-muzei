Anime Muzei
===========

An open source plugin for Roman Nurik's [Muzei Live Wallpaper](https://play.google.com/store/apps/details?id=net.nurik.roman.muzei&hl=en)
which serves wallpapers from anime and manga series.

Download
========
[<img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="300"></img>](https://play.google.com/store/apps/details?id=codes.fabio.animemuzei)

Build
=====
In order to build you need to generate an Imgur Api key [here](https://api.imgur.com/oauth2/addclient).

Steps:

1. Application Name: `anime-muzei`
2. Choose: `OAuth 2 authorization without a callback URL`
3. Fill in `email` and `description`
4. ✓ I'm not a robot
5. Create `app/gradle.properties` and put in `imgurPublicKey=YOUR_GENERATED_API_KEY`


License
=======

    Copyright 2016 Fabian Shallari

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
