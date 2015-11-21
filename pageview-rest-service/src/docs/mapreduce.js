db.pageView.mapReduce(
    function() {
        emit(this.viewer.session, {user: this.viewer.userEmail, sum:1})
    },
    
    function(key,values) {
        return {user: values[0].user, sum: values.length}
    },
                      
    { out: "referer"}
)