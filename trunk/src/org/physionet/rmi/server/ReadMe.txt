To have have the service running on the server:

1. Start the registry from a terminal:

rmiregistry &

2. Start the Server class:

java -cp /home/ikaro/workspace/wfdb-java-app/build/ -Djava.rmi.server.codebase=file://localhost/home/ikaro/physionetcompute.jar -Djava.rmi.server.hostname=192.168.1.42 -Djava.security.policy=/home/ikaro/workspace/wfdb-java-app/src/org/physionet/rmi/engine/server.policy org.physionet.rmi.engine.Co