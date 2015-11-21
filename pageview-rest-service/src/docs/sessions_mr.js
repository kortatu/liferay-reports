db.pageView.mapReduce(
    function() {
        for (i = 0; i < this.page.portlets.length; i++) {         
            emit(this.viewer.session, 1)
        }
    },
    function(key,values) {return Array.sum(values)},
                      { out: "sessions"}
)