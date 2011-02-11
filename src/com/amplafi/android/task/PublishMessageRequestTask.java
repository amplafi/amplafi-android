package com.amplafi.android.task;

import java.net.URI;

import static org.amplafi.flow.auth.StandardFlowRequestParameters.*;

public class PublishMessageRequestTask extends HttpGetRequestTask {


	public PublishMessageRequestTask(URI flowServerURI,
			CharSequence flowClientId, long envelopeId, CharSequence title,
			CharSequence description) {
		super(flowServerURI, flow, "CreateAlert", flowClientUserId, flowClientId, "broadcastEnvelope", envelopeId,
				"messageBody", description, "messageHeadline", title);
	}

}
