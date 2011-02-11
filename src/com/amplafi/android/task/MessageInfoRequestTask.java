package com.amplafi.android.task;

import static org.amplafi.flow.auth.StandardFlowRequestParameters.flow;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.fsAdvanceTo;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.fsCompleteFlow;

import java.net.URI;

public class MessageInfoRequestTask extends HttpGetRequestTask {

	public MessageInfoRequestTask(URI requestUri, CharSequence flowClientId,
			long envelopeId) {
		super(requestUri, flowClientUserId, flowClientId, flow, "CreateAlert",
				"broadcastEnvelope", envelopeId, fsAdvanceTo, "content",
				fsCompleteFlow, "fap");
	}

}
