//Doesn't handle FIELDSET
function clickHandler(e){
	var elem, evt = e ? e:event;
	if (evt.srcElement)  elem = evt.srcElement;
	else if (evt.target) elem = evt.target;

	var targetId = elem.id;
	try{
		var portlet_id = AUI().one(elem).ancestor('.portlet-boundary').get('children').item(0).attr('id').substring(2);
		var jsonInfo = '{"'+"portletId"+'":"'+portlet_id+"\",\"tagName"
						+'":"'+elem.tagName+"\",\""
						+"userId"+'":"'+Liferay.ThemeDisplay.getUserId()+"\",\""
						+"t"+'":"'+new Date().getTime()+"\",\""
						+"targetFieldId"+'":"'+elem.id+"\",\""
						+"plid"+'":"'+Liferay.ThemeDisplay.getPlid()+"\",\""
						+"companyId"+'":"'+Liferay.ThemeDisplay.getCompanyId()+"\",\""
						+"scopeGroupId"+'":"'+Liferay.ThemeDisplay.getScopeGroupId()+"\"";
		
		var portletURL = Liferay.PortletURL.createResourceURL();
		var content="";

		
		if( elem.tagName.toLowerCase()=='button' || 
			elem.tagName.toLowerCase()=='div' ||
			elem.tagName.toLowerCase()=='span' ||
			elem.tagName.toLowerCase()=='caption' ||
			elem.tagName.toLowerCase()=='td' || 
			elem.tagName.toLowerCase()=='th'
				
		){
			content = processText(elem);
		}
		
		if(elem.tagName.toLowerCase()=='option'){
			content=processOption(elem);
		}
		
		if(elem.tagName.toLowerCase()=='input'){
			content = processInput(elem);
		}

		if(elem.tagName.toLowerCase()=='img'){
			content = processImg(elem);
		}
		
		if( elem.tagName.toLowerCase()=='textarea'||
			elem.tagName.toLowerCase()=='label'||
			elem.tagName.toLowerCase()=='p' ||
			elem.tagName.toLowerCase()=='a' ||
			elem.tagName.toLowerCase()=='h1'||
			elem.tagName.toLowerCase()=='h2'||
			elem.tagName.toLowerCase()=='h3'||
			elem.tagName.toLowerCase()=='h4'||
			elem.tagName.toLowerCase()=='h5'||
			elem.tagName.toLowerCase()=='h6'){
			
			content = processContent(elem);
		}

		jsonInfo= jsonInfo+content+'}'

		//console.log(jsonInfo);
		
		portletURL.setPortletId("liferayreports_WAR_liferayreports");
		portletURL.setResourceId("clientInfo");
		portletURL.setParameter("info", JSON.stringify(jsonInfo));
		//portletURL.setParameter("info", jsonInfo);
		

		// Must define the call		
		YUI().use('aui-io-request',
			function (A) {
				A.io.request(portletURL.toString(),
				{
					method: "POST",
					cache: false,
					on: {
						success: function() {

						},
						failure: function(){
							
						}
					}
				});
	
			}
		);
	}catch(e){}
	return true;
}

//CAMBIAR OBVIAMENTE
if(true){ 
	document.onclick=clickHandler;
}

//button + div + span
function processText(elem){
	var value="";
	value= getParamName("text")+elem.textContent+"\"";
	return value;
}
//input text - checkbox
function processInput(elem){
	var value="";
	if(elem.type=="checkbox"){
		value= getParamName("text")+elem.checked+"\"";
	}else{
		value= getParamName("text")+elem.value+"\"";
	}
	return value;	
}
//images
function processImg(elem){
	var value="";
	value= getParamName("text")+elem.src+"\"";
	return value;
}
//label + p + a + h* + textarea
function processContent(elem){
	var value="";
	value= getParamName("text")+elem.innerHTML+"\"";
	return value;	
}
//OPTION
function processOption(elem){
	var value="";
	value= processText(elem);
	value= value+getParamName("id")+elem.value+"\"";
	return value;
}

function getParamName(name){
	return ",\""+name+"\":"+"\"";
}