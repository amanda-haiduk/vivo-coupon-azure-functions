package com.function;

import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import com.function.repository.CouponRepositoryImpl;

public class FunctionTest {
    private CouponRepositoryImpl classUnderTest;
    private Map<String, String> overriddenSettings = new HashMap<>();

    @BeforeEach
    public void setup() {
        classUnderTest = new CouponRepositoryImpl() {
            @Override
            protected String getSetting(String setting) {
                return overriddenSettings.get(setting);
            }
        };
    }

    @Test
    public void testHttpTriggerJava() throws Exception {
        
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("category", "voucher_vivo_play_welcome");
        doReturn(queryParams).when(req).getQueryParameters();

        final Optional<String> queryBody = Optional.empty();
        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                overriddenSettings.put("couponCategoriesId", "voucher");
                String userId = "aaaa";
                String[] categoryNameReq = new String[]{"voucher_vivo_play_welcome"};
                classUnderTest.findCouponsWelcome(userId, categoryNameReq);   
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        
        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        
        // Invoke
        final HttpResponseMessage ret = new Function().run(req,"users/aaaa",context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }
}
