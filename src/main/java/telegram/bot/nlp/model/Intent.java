/*
 * Id: Intent.java,v $ Jul 27, 2016 antonia Exp Copyright (c) 2e Systems GmbH This software is the confidential and
 * proprietary information. You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into.
 */
package telegram.bot.nlp.model;

import java.util.HashMap;
import java.util.Map;

public class Intent {

    private IntentType intentType;
    private Map<IntentParameter, Object> context = new HashMap<>();

    public Intent(IntentType intentType) {
        this.intentType = intentType;
    }

    public Intent(IntentType intentType, Map<IntentParameter, Object> context) {
        this.intentType = intentType;
        this.context = context;
    }

    public IntentType getIntentType() {
        return intentType;
    }

    public Map<IntentParameter, Object> getContext() {
        return context;
    }
}
