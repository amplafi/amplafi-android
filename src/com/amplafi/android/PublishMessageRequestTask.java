package com.amplafi.android;

import java.net.URI;

import com.amplafi.android.task.HttpGetRequestTask;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.*;

public class PublishMessageRequestTask extends HttpGetRequestTask {


	public PublishMessageRequestTask(URI flowServerURI,
			CharSequence flowClientId, long envelopeId, CharSequence title,
			CharSequence description) {
		super(flowServerURI, flow, "CreateAlert", flowClientUserId, flowClientId, "broadcastEnvelope", envelopeId,
				"messageBody", description, "messageHeadline", title);
	}

}
