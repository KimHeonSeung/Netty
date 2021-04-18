### Server - Client Communicating with Serialized Object  
  
### Server Steps  
1. Start with main method in NettyServer class.  
2. Server Settings are wrote in conf/server.properties.  
3. Socket open and wait for connection in NettyServerInitializer class.  
4. Client Channel Handling in NettyServerInboundHandler class  
  
### Client Steps   
1. Start with main method in NettyClient class.  
2. Client Settings are wrote in conf/client.properties.  
3. Try to connect to Server in NettyClientInitializer class.  
4. Check integrity of some files after connection established and send to server with RequestVO.  
5. Server Channel Handling in NettyClientInboundHandler class.  
