function() {
    for (i = 0; i < this.page.portlets.length; i++) {         
        emit(this.page.portlets[i].portletId, 1)
    }
}