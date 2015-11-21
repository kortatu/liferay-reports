<%@ include file="init.jspf" %>


<liferay-portlet:renderURL var="editUrl"  portletMode="edit">
</liferay-portlet:renderURL>

<a href="${editUrl}">Editar configuracion</a>

The reports system is enabled? ${enabled}<br/>
Jaskula is local? ${localJaskula}

