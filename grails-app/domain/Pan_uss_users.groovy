class Pan_uss_users {

    String user_id

    String login_id

    String user_name

    String user_sign

    String user_property

    String user_first

    String regist_time

    String pass_modify

    String password

    String account_status

    String lock_time

    String expired_time

    String corp_id

    String owner_organ

    String is_alert

    String employee_id

    String employee_name

    String alert_type

    String is_used

    String unuse_date

    Integer global_seq

    Integer seq

    String rtx_is_user

    String rtx_date

    String rtx_userid

    String rtx_password

    String note

    String user_sso

    String user_email

    static mapping = {
        // table 'Pan_uss_users'

        version false
        id composite: ['F_ID']



        user_id column: 'USER_ID'

        login_id column: 'LOGIN_ID'

        user_name column: 'USER_NAME'

        user_sign column: 'USER_SIGN'

        user_property column: 'USER_PROPERTY'

        user_first column: 'USER_FIRST'

        regist_time column: 'REGIST_TIME'

        pass_modify column: 'PASS_MODIFY'

        password column: 'PASSWORD'

        account_status column: 'ACCOUNT_STATUS'

        lock_time column: 'LOCK_TIME'

        expired_time column: 'EXPIRED_TIME'

        corp_id column: 'CORP_ID'

        owner_organ column: 'OWNER_ORGAN'

        is_alert column: 'IS_ALERT'

        employee_id column: 'EMPLOYEE_ID'

        employee_name column: 'EMPLOYEE_NAME'

        alert_type column: 'ALERT_TYPE'

        is_used column: 'IS_USED'

        unuse_date column: 'UNUSE_DATE'

        global_seq column: 'GLOBAL_SEQ'

        seq column: 'SEQ'

        rtx_is_user column: 'RTX_IS_USER'

        rtx_date column: 'RTX_DATE'

        rtx_userid column: 'RTX_USERID'

        rtx_password column: 'RTX_PASSWORD'

        note column: 'NOTE'

        user_sso column: 'USER_SSO'

        user_email column: 'USER_EMAIL'

    }

    static constraints = {

        user_id(nullable: false)

        user_name(nullable: false)

        user_sign(nullable: false)

        user_property(nullable: false)

        user_first(nullable: false)

        password(nullable: false)

        account_status(nullable: false)

        corp_id(nullable: false)

        owner_organ(nullable: false)

        is_alert(nullable: false)

        employee_id(nullable: false)

        alert_type(nullable: false)

        is_used(nullable: false)

        global_seq(nullable: false)

        seq(nullable: false)

    }
}