function() {
        if (this.headers && this.headers.referer) {
            var index = this.headers.referer.indexOf('?')
            if (index < 0) index = this.headers.referer.length;
            var referer = this.headers.referer.substring(0,index);
            emit(referer, 1)
        } else {
            emit("",1)
        }
    }