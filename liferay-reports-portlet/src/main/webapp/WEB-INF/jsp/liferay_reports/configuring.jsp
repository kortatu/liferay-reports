<%@ include file="init.jspf" %>


<liferay-portlet:renderURL var="viewUrl"  portletMode="view">
</liferay-portlet:renderURL>

<liferay-portlet:actionURL var="switchJaskulaLocalUrl"  portletMode="edit">
    <portlet:param name="action" value="switchJaskula"/>
</liferay-portlet:actionURL>

<liferay-portlet:actionURL var="switchReportsAgent"  portletMode="edit">
    <portlet:param name="action" value="switchReports"/>
</liferay-portlet:actionURL>

<br/><a href="${viewUrl}">Volver</a>
<br/>The reports system is enabled? <input type="checkbox" <c:if test="${enabled}">CHECKED</c:if> name="enabled"/> <a href="${switchReportsAgent}">Switch Enabled/Disabled</a>
<br/>Jaskula is local? ${localJaskula} <a href="${switchJaskulaLocalUrl}">Switch Local/Remote</a>
