package com.amplafi.android.task;

import static org.amplafi.flow.auth.StandardFlowRequestParameters.flow;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.flowClientUserId;
import static org.amplafi.flow.auth.StandardFlowRequestParameters.fsAdvanceTo;

import java.net.URI;

public abstract class MessageListRequestTask extends HttpGetRequestTask {

	public MessageListRequestTask(URI flowServerURI, String clientId) {
		super(flowServerURI, flowClientUserId, clientId,
				flow, "ApprovedList", fsAdvanceTo, "Messages", fsAdvanceTo, "fap");
	}
}
