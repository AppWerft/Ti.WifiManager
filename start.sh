#!/bin/bash

APPID=ti.wifimanager
VERSION=1.0.3

#cp android/assets/* iphone/
cd android;rm -rf build/classes;ant clean;mkdir build/generated/java;ant -v;  unzip -uo  dist/$APPID-android-$VERSION.zip  -d  ~/Library/Application\ Support/Titanium/;cd ..
#cd iphone/; python build.py;  unzip -uo  $APPID-iphone-$VERSION.zip  -d  ~/Library/Application\ Support/Titanium/;cd ..
cp android/dist/$APPID-android-$VERSION.zip .
#cp iphone/$APPID-iphone-$VERSION.zip .
