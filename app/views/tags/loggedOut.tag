#{if !"/register".equals(request.url) && (session.username == null || session.username.length() == 0)}
    #{doBody /}
#{/if}