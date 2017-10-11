# This script package the fileAV package for
# Client program

import zipfile
import datetime
import ConfigParser
import os

# Copy the default file here and modify the desired options

#######################################
#[MEDIA_CONFIG]
#;每秒发送的视频帧数
#video_quality=7
#
#;是否使用文件作音频输入, 以及文件名
#use_audio_file=0
#audio_file=..\AVClips\example.wav
#
#;是否使用文件作视频输入, 以及文件名
#use_video_file=0
#video_file=..\AVClips\video.dat
#######################################

cmd = 'copy ..\\..\\..\\src\\Client\\conf\\Flvcc.ini.template .\\Flvcc.ini'
print(cmd)
os.system(cmd)

config = ConfigParser.ConfigParser()
config.read('Flvcc.ini')
config.set('MEDIA_CONFIG', 'video_quality', '5')
config.set('MEDIA_CONFIG', 'use_audio_file', '1')
config.set('MEDIA_CONFIG', 'audio_file', '..\\AVClips\\example.wav')
config.set('MEDIA_CONFIG', 'use_video_file', '1')
config.set('MEDIA_CONFIG', 'video_file', '..\\AVClips\\video.dat')
inifile = open('Flvcc.ini', 'w')
config.write(inifile)
inifile.close()


# Get the zip file name
now = datetime.datetime.now()
filename = 'FileAVPackage-%04d%02d%02d%02d%02d%02d.zip' % \
        (now.year, now.month, now.day, now.hour, now.minute, now.second)

# Pack the file
newpackage = zipfile.ZipFile(filename, 'w', zipfile.ZIP_DEFLATED)
newpackage.write('../../../src/Client/AVClips/example.wav', 'AVClips/example.wav')
newpackage.write('../../../src/Client/AVClips/video.dat', 'AVClips/video.dat')
newpackage.write('Flvcc.ini', 'conf/Flvcc.ini')
newpackage.write('Readme.txt', 'Readme.txt')
newpackage.close()

# Remove temporary file
os.unlink('Flvcc.ini')

