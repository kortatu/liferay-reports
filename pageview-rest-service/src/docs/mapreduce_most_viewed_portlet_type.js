db.pageView.mapReduce(
		function() {
		    for (i = 0; i < this.page.portlets.length; i++) {
		    	var portletId = this.page.portlets[i].portletId
			if(portletId !== undefined) {
		    		var indexOf = portletId.indexOf("_INSTANCE_")
		    		if (indexOf>0) {
		    			portletId = portletId.slice(0,indexOf)
		    		}
		        	emit(portletId, 1)
			}
		    }
		},
    	function(key,values) {return Array.sum(values)},
                      
    { out: "mostViewedPortletTypes"}
)
