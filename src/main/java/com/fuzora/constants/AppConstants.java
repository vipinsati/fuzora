package com.fuzora.constants;

public class AppConstants {
	private AppConstants() {}

	public static final String TRIGGER = "trigger";
	public static final String FILTER = "filter";
	public static final String TRANSFORMER = "transformer";
	public static final String ACTION = "action";

	public static final String T_PROTOCOL = "triggerProtocol";
	public static final String A_PROTOCOL = "actionProtocol";

	public static final String AMQP_INPUT = "rabbitInput";
	public static final String HTTP_POLLING_INPUT = "httpPollingInput";
	public static final String HTTP_INPUT = "http_input";
	public static final String SFTP_INPUT = "sftp_input";

	public static final String AMQP_OUTPUT = "rabbitOutput";
	public static final String HTTP_OUTPUT = "http_output";
	public static final String SFTP_OUTPUT = "sftps_output";

}
