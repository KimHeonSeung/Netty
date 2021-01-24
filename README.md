# Netty  
  
  
  
### 직렬화된 서버 - 클라이언트간의 통신 구현  

## 서버  
- NettyServer의 main 함수 실행  
- conf/server.properties 파일을 읽어 서버 구동 세팅  
- NettyServerInitializer를 통해 소켓 오픈 및 연결 대기  
- NettyServerInboundHandler에서 클라이언트 채널에 관한 처리  
  
## 클라이언트  
- NettyClient의 main 함수 실행  
- conf/client.properties 파일을 읽어 구동 세팅  
- NettyClientInitializer를 통해 서버 연결 요청  
- 연결 직후 특정 파일의 무결성에 관한 RequestVO 객체 전송  
- NettyClientInboundHandler에서 서버 채널에 관한 처리  
