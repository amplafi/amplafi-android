HOWTO

..Set up development environment

1. Install Maven 2.2.1
2. Install latest Android SDK
3. Set environment variable ANDROID_HOME to the path of your installed Android SDK and add $ANDROID_HOME/tools to your $PATH. (or on Windows %ANDROID_HOME%/tools)
4. clone Amplafi Opensource Parent:
    'git clone https://aectann@github.com/amplafi/amplafi-opensource-parent.git'
5. clone Amplafi Android:
    'git clone https://aectann@github.com/amplafi/amplafi-android.git'

..Set up Eclipse
1. switch to the project directory: 
    'cd amplafi-android'
2. make maven to create eclipse project files: 
    'mvn eclipse:eclipse'
3. Import the project from eclipse 

..Deploy apk to a device

1. Connect a device
2. switch to the project directory 
    'cd amplafi-android'
2. run 
    'mvn install android:deploy'

..Debug

1. run 'ddms' from command line
2. Connect with your debugger to port 8700

..Get more info about android-maven-plugin targets
1. 'mvn help:describe -Dplugin=com.jayway.maven.plugins.android.generation2:maven-android-plugin:2.8.4'

