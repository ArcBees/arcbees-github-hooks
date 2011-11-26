<%@ page import="arcbees.github.hooks.GitUser" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main"/>
    <g:set var="entityName" value="${message(code: 'gitUser.label', default: 'GitUser')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="nav">
    <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a>
    </span>
    <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label"
                                                                           args="[entityName]"/></g:link></span>
</div>

<div class="body">
    <h1><g:message code="default.create.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${gitUserInstance}">
        <div class="errors">
            <g:renderErrors bean="${gitUserInstance}" as="list"/>
        </div>
    </g:hasErrors>
    <g:form action="save">
        <div class="dialog">
            <table>
                <tbody>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="basecampUser"><g:message code="gitUser.basecampUser.label"
                                                             default="Basecamp User"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: gitUserInstance, field: 'basecampUser', 'errors')}">
                        <g:select name="basecampUser.id" from="${arcbees.github.hooks.BasecampUser.list()}"
                                  optionKey="id" value="${gitUserInstance?.basecampUser?.id}"/>
                    </td>
                </tr>

                <tr class="prop">
                    <td valign="top" class="name">
                        <label for="email"><g:message code="gitUser.email.label" default="Email"/></label>
                    </td>
                    <td valign="top" class="value ${hasErrors(bean: gitUserInstance, field: 'email', 'errors')}">
                        <g:textField name="email" value="${gitUserInstance?.email}"/>
                    </td>
                </tr>

                </tbody>
            </table>
        </div>

        <div class="buttons">
            <span class="button"><g:submitButton name="create" class="save"
                                                 value="${message(code: 'default.button.create.label', default: 'Create')}"/></span>
        </div>
    </g:form>
</div>
</body>
</html>
