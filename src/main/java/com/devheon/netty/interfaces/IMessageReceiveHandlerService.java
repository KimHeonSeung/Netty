package com.devheon.netty.interfaces;

import com.devheon.netty.common.vo.RequestVO;
import com.devheon.netty.common.vo.ResponseVO;

/**
 * <pre>
 * Description :
 *     수신 메세지 분기처리 후 핸들링 인터페이스
 * ===============================================
 * Methods :
 *     void handleRequestMessage(RequestVO reqVo)
 *     void handleResponseMessage(ResponseVO resVo)
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2020-03-27
 * </pre>
 */
public interface IMessageReceiveHandlerService {
	void handleRequestMessage(RequestVO requestVO);
	void handleResponseMessage(ResponseVO responseVO);
}
