package oracle.ted.ipspregled.bean;

import oracle.adf.share.ADFContext;

public class NATSecurityBean {
    public NATSecurityBean() {
    }
    public boolean isAuthorizationEnabled() {
            return (ADFContext.getCurrent().getSecurityContext().isAuthorizationEnabled());
        }
}
