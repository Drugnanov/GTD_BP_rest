/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.restapi;

/**
 *
 * @author slama
 */
public final class ApiConstants {

    public static final String INTERVAL_FROM = "from";
    public static final String INTERVAL_TO = "to";

    public static final String PROJECT_ID = "id";
    public static final String PROJECT_TITLE = "title";
    public static final String PROJECT_PARENT = "project";

    public static final String TASK_ID = "id";
    public static final String TASK_TITLE = "title";
    public static final String TASK_PARENT = "project";

    public static final String STATE = "state";
    public static final String STATE_TITLE = "title";
    public static final String STATE_CODE = "code";
    public static final String STATE_DESCRIPTION = "description";

    public static final String NOTE_ID = "id";
    public static final String NOTE_PARENT = "parent";
    public static final String NOTE_PARENT_TASK = "task";
    public static final String NOTE_PARENT_PROJECT = "project";
    public static final String NOTE_ORDER = "order";

    public static final String CONTEXT = "context";

    public static final String FILTER_TITLE = "title";
    public static final String FILTER_OWNER = "owner";

    public static final int TEST_USER_ID = 1;
    public static final String TEST_USER_LOGIN = "jannovak";

    private ApiConstants() {
    }
}
