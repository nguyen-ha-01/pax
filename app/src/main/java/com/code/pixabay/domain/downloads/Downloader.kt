package com.code.pixabay.domain.downloads

interface Downloader {
    //subpath : file name after downloading
    fun download(url: String, type: String ,title : String?,decription: String?  ,subPath:String): Long
}