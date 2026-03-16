package org.example.trigger.http;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.api.IMCPGatewayService;
import org.example.cases.IMCPSessionService;
import org.example.types.enums.ResponseCode;
import org.example.types.exception.AppException;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author wyh
 */
@Slf4j
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class MCPSessionController implements IMCPGatewayService {

    @Resource
    private IMCPSessionService imcpSessionService;

    @GetMapping("{gatewayId}/mcp/sse")
    @Override
    public Flux<ServerSentEvent<String>> establishSSEConnection(@PathVariable String gatewayId) throws Exception {
        log.info("尝试建立 SSE 连接");
        try {
            if (StringUtils.isBlank(gatewayId)){
                log.info("参数错误，连接失败");
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }

            return imcpSessionService.createMCPSession(gatewayId);
        } catch (Exception e) {
            log.info("建立 SSE 连接失败");
            throw new RuntimeException(e);
        }
    }
}
