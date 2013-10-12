class Pan_uss_users implements Serializable {

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
        table 'PAN_USS_USERS'

        id composite: ['user_id']

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

        user_id(blank: true, nullable: true, size: 0..30)

        login_id(blank: true, nullable: true, size: 0..30)

        user_name(blank: true, nullable: true, size: 0..30)

        user_sign(blank: true, nullable: true, size: 0..2)

        user_property(blank: true, nullable: true, maxSize: 1)

        user_first(blank: true, nullable: true, maxSize: 1)

        regist_time(blank: true, nullable: true, size: 0..17)

        pass_modify(blank: true, nullable: true, size: 0..17)

        password(blank: true, nullable: true, size: 0..128)

        account_status(blank: true, nullable: true, size: 0..2)

        lock_time(blank: true, nullable: true, size: 0..17)

        expired_time(blank: true, nullable: true, size: 0..17)

        corp_id(blank: true, nullable: true, size: 0..30)

        owner_organ(blank: true, nullable: true, size: 0..30)

        is_alert(blank: true, nullable: true, maxSize: 1)

        employee_id(blank: true, nullable: true, size: 0..30)

        employee_name(blank: true, nullable: true, size: 0..60)

        alert_type(blank: true, nullable: true, size: 0..2)

        is_used(blank: true, nullable: true, maxSize: 1)

        unuse_date(blank: true, nullable: true, size: 0..17)

        global_seq(blank: true, nullable: true, size: 0..10)

        seq(blank: true, nullable: true, size: 0..10)

        rtx_is_user(blank: true, nullable: true, maxSize: 1)

        rtx_date(blank: true, nullable: true, size: 0..17)

        rtx_userid(blank: true, nullable: true, size: 0..30)

        rtx_password(blank: true, nullable: true, size: 0..30)

        note(blank: true, nullable: true, size: 0..255)

        user_sso(blank: true, nullable: true, size: 0..50)

        user_email(blank: true, nullable: true, size: 0..50)

    }
}