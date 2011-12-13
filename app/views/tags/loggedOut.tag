#{if session.username == null || session.username.length() == 0}
    #{doBody /}
#{/if}