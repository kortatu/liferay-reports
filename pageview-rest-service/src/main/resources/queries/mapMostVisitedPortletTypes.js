function() {
    for (i = 0; i < this.page.portlets.length; i++) {
    	var portletId = this.page.portlets[i].portletId
    	var indexOf = portletId.indexOf("_INSTANCE_")
    	if (indexOf>0) {
    		portletId = portletId.slice(0,indexOf)
    	}
        emit(portletId, 1)
    }
}