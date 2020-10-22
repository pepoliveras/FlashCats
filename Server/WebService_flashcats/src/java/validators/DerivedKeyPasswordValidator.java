/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import com.sun.xml.wss.impl.callback.PasswordValidationCallback;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.DerivedKeyPasswordRequest;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.PasswordValidationException;
import com.sun.xml.wss.impl.callback.PasswordValidationCallback.Request;

/**
 *
 * @author jzorita
 * @version
 *
 */
public class DerivedKeyPasswordValidator extends PasswordValidationCallback.DerivedKeyPasswordValidator {
    
    public DerivedKeyPasswordValidator() {
    }
    
    @Override
    public void setPassword(Request request) {
        if (request instanceof DerivedKeyPasswordRequest) {
            ((DerivedKeyPasswordRequest) request).setPassword("changeit");
        }
    }
    
    @Override
    public boolean validate(Request rqst) throws PasswordValidationException {
        return true;
    }
    
}
