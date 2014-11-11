package com.broccoli.hacca.searchapi;

import com.broccoli.hacca.pageinfo.PageInfo;

public interface OnSearchAPIListener {
	void onSuccessSearch(PageInfo pageInfo);
	void onFailSearch();
}
