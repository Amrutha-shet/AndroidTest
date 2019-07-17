package com.example.androidtest.model.repository


class ResponseRepo {
    companion object {
        private var newsRepository: ResponseRepo? = null
        val instance: ResponseRepo
            get() {
                if (newsRepository == null) {
                    newsRepository = ResponseRepo()
                }
                return newsRepository as ResponseRepo
            }
    }
}