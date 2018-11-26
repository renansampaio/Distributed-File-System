javac *.java
ping 127.0.0.1 -n 2 > nul
rmic Proxy
ping 127.0.0.1 -n 2 > nul
rmic DataNode
ping 127.0.0.1 -n 2 > nul
start rmiregistry
ping 127.0.0.1 -n 2 > nul
start java Proxy
ping 127.0.0.1 -n 2 > nul
start java DataNode
ping 127.0.0.1 -n 2 > nul
start java ClientDFS