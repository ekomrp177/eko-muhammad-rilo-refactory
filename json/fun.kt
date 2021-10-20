@Parcelize
data class Placement(
    var roomId: String = "",
    var name: String = ""
): Parcelable

@Parcelize
data class InventoryApiEntity(
    var inventoryId: String = "",
    var name: String = "",
    var type: String = "",
    var tags: ArrayList<String> = ArrayList<String>(),
    var purchasedAt: String = "",
    var placement: Placement = Placement(),
): Parcelable

// 1. Find items in the Meeting Room.
fun findItemsMeetingRoom()LiveData<List<InventoryApiEntity>> {
    val url = "data.json"
    val fixItems = MutableLiveData<List<InventoryApiEntity>>()
    val tempItems = ArrayList<InventoryApiEntity>()
    AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(object : JSONArrayRequestListener {
                        override fun onResponse(list: JSONArray?) {
                            if (list != null) {
                                for (i in 0 until list.length()) {
                                    val getList = list.getJSONObject(i)
                                    val tempList = InventoryApiEntity()
                                    val placement = getList.getJSONArray("placement")
                                    val contains = placement.any(it.getString("name").toLowerCase() == "Meeting Room".toLowerCase())
                                    if (contains) {
                                        val tags = getList.getJSONArray("tags")
                                        tempList.tags.addAll(tags)
                                        tempList.inventoryId = getList.getString("inventory_id")
                                        tempList.name = getList.getString("name")
                                        tempList.type = getList.getString("type")
                                        tempList.purchasedAt = getList.getString("purchased_at")
                                        val placementRoomId = placement.getString("room_id")
                                        val placementName = placement.getString("name")
                                        tempList.placement = Placement(placementRoomId, placementName)
                                        tempItems.postValue(tempList)
                                    }
                                }
                                fixItems.postValue(tempItems)
                            }
                        }
                        override fun onError(anError: ANError?) {
                            fixItems.postValue(null)
                        }
                    });   
    return fixItems
}

// 2. Find all electronic devices.
// 3. Find all furniture.
// just do input with "electronic" or "furniture"
fun findItemsByType(query: String)LiveData<List<InventoryApiEntity>> {
    val url = "data.json"
    val fixItems = MutableLiveData<List<InventoryApiEntity>>()
    val tempItems = ArrayList<InventoryApiEntity>()
    AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(object : JSONArrayRequestListener {
                        override fun onResponse(list: JSONArray?) {
                            if (list != null) {
                                for (i in 0 until list.length()) {
                                    val getList = list.getJSONObject(i)
                                    val tempList = InventoryApiEntity()
                                    val type = getList.getString("type")
                                    if (type.toLowerCase() == query.toLowerCase()) {
                                        val tags = getList.getJSONArray("tags")
                                        tempList.tags.addAll(tags)
                                        tempList.inventoryId = getList.getString("inventory_id")
                                        tempList.name = getList.getString("name")
                                        tempList.type = getList.getString("type")
                                        tempList.purchasedAt = getList.getString("purchased_at")
                                        val placementRoomId = placement.getString("room_id")
                                        val placement = getList.getJSONArray("placement")
                                        val placementName = placement.getString("name")
                                        tempList.placement = Placement(placementRoomId, placementName)
                                        tempItems.postValue(tempList)
                                    }
                                }
                                fixItems.postValue(tempItems)
                            }
                        }
                        override fun onError(anError: ANError?) {
                            fixItems.postValue(null)
                        }
                    }
    return fixItems
}

// 4. Find all items were purchased on 16 Januari 2020
// input date "2021-01-16" as dateQuery
fun findItemsByTags(dateQuery: String)LiveData<List<InventoryApiEntity>> {
    val url = "data.json"
    val fixItems = MutableLiveData<List<InventoryApiEntity>>()
    val tempItems = ArrayList<InventoryApiEntity>()
    AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(object : JSONArrayRequestListener {
                        override fun onResponse(list: JSONArray?) {
                            if (list != null) {
                                for (i in 0 until list.length()) {
                                    val getList = list.getJSONObject(i)
                                    val tempList = InventoryApiEntity()
                                    val date = getList.getString("purchased_at")
                                    
                                    var dateInString = java.time.format.DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(date))
                                    dateInString = dateInString.substring(0, 10)
                                    if (dateInString == dateQuery) {
                                        val tags = getList.getJSONArray("tags")
                                        tempList.tags.addAll(tags)
                                        tempList.inventoryId = getList.getString("inventory_id")
                                        tempList.name = getList.getString("name")
                                        tempList.type = getList.getString("type")
                                        tempList.purchasedAt = getList.getString("purchased_at")
                                        val placementRoomId = placement.getString("room_id")
                                        val placement = getList.getJSONArray("placement")
                                        val placementName = placement.getString("name")
                                        tempList.placement = Placement(placementRoomId, placementName)
                                        tempItems.postValue(tempList)
                                    }
                                }
                                fixItems.postValue(tempItems)
                            }
                        }
                        override fun onError(anError: ANError?) {
                            fixItems.postValue(null)
                        }
                    }
    return fixItems
}


// 5. Find all items with brown color
fun findItemsByTags(query: String)LiveData<List<InventoryApiEntity>> {
    val url = "data.json"
    val fixItems = MutableLiveData<List<InventoryApiEntity>>()
    val tempItems = ArrayList<InventoryApiEntity>()
    AndroidNetworking.get(url)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONArray(object : JSONArrayRequestListener {
                        override fun onResponse(list: JSONArray?) {
                            if (list != null) {
                                for (i in 0 until list.length()) {
                                    val getList = list.getJSONObject(i)
                                    val tempList = InventoryApiEntity()
                                    val tags = getList.getJSONArray("tags")
                                    val contains = placement.any(it.toLowerCase() == query.toLowerCase())
                                    if (contains) {
                                        tempList.tags.addAll(tags)
                                        tempList.inventoryId = getList.getString("inventory_id")
                                        tempList.name = getList.getString("name")
                                        tempList.type = getList.getString("type")
                                        tempList.purchasedAt = getList.getString("purchased_at")
                                        val placementRoomId = placement.getString("room_id")
                                        val placement = getList.getJSONArray("placement")
                                        val placementName = placement.getString("name")
                                        tempList.placement = Placement(placementRoomId, placementName)
                                        tempItems.postValue(tempList)
                                    }
                                }
                                fixItems.postValue(tempItems)
                            }
                        }
                        override fun onError(anError: ANError?) {
                            fixItems.postValue(null)
                        }
                    }
    return fixItems
}
