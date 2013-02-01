To run the application on the Client side:

java -cp /home/ikaro/workspace/wfdb-java-app/build/ -Djava.rmi.server.codebase=file://localhost/home/ikaro/workspace/wfdb-java-app/build/ -Djava.security.policy=/home/ikaro/workspace/wfdb-java-app/src/org/physionet/rmi/client/client.policy org.physionet.rmi.client.ComputePi localhost 45