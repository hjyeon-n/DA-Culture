package jy.project.review.page;

public class pageMaker {
    private int totalCount;
    private int countList;
    private int pageNum; 
    private int contentNum = 10;
    private int startPage = 1;
    private int endPage = 5; 
    private boolean prev = false;
    private boolean next; 
    private int currentBlock = 1;
    private int lastBlock;
    private int range = 5;
    private int startList;
    
	public int getStartList() {
		return startList;
	}

	public void setStartList(int startList) {
		this.startList = startList;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean isPrev() {
        return prev;
    }
 
    public void setPrev() {
    	if (this.getCurrentBlock() == 1) {
    		this.prev = false;
    	}
    	else {
    		this.prev = true;
    	}
    }
 
    public boolean isNext() {
        return next;
    }
 
    public void setNext() {
    	if (this.totalCount < range * this.contentNum) {
      		this.next = false;
      	}
      	 else if(getLastBlock() == getCurrentBlock()) {
         	this.next = false;        
         }
         else {
            this.next = true;
        }
    }
 
    public int getStartPage() {
        return startPage;
    }
 
    public void setStartPage(int currentBlock) {
        this.startPage = (currentBlock * range) - (range - 1);
    }
 
    public int getEndPage() {
        return endPage;
    }
 
    public void setEndPage(int getlastblock,int getcurrentblock) {
        if (getlastblock==getcurrentblock) {
            this.endPage = calcPage(getTotalCount(),getContentNum());
        }
        else {
            this.endPage = getStartPage() + (range - 1);
        }
    }
    
    public int getCurrentBlock() {
        return currentBlock;
    }
 
    public void setCurrentBlock(int pageNum) {
        this.currentBlock = pageNum / range;
        if (pageNum % range > 0) {
            this.currentBlock++;
        }
    }
 
    public int getLastBlock() {
        return lastBlock;
    }
 
    public void setLastBlock(int totalCount) {
    	this.lastBlock = totalCount / (range * this.contentNum);
        if (totalCount % (range * this.contentNum) > 0) {
            this.lastBlock++;
        }
    }
  
    public int calcPage(int totalCount, int contentNum) {
        
    	int totalPage = totalCount / contentNum;
        
        if (totalCount % contentNum > 0) {
            totalPage++;
        }
        
        if (totalPage < this.pageNum) {
            this.pageNum = totalPage;
        }
        
        return totalPage;
    }
 
    public int getTotalCount() {
        return totalCount;
    }
 
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    
    public int getCountList() {
        return countList;
    }
 
    public void setCountList(int countlist) {
        this.countList = countlist;
    }
 
    public int getPageNum() {
        return pageNum;
    }
 
    public void setPageNum(int pageNum) {
    	this.pageNum = pageNum;
    }
 
    public int getContentNum() {
        return contentNum;
    }
 
    public void setContentNum(int contentNum) {
        this.contentNum = contentNum;
    } 
    
    public void pageInfo(int pageNum, int contentNum, int totalCount) {
    	this.setTotalCount(totalCount);
    	this.setCurrentBlock(pageNum);
    	this.setLastBlock(totalCount);
    	this.setPageNum(pageNum - 1);
        this.setContentNum(contentNum);
        this.setStartList((pageNum - 1) * contentNum); 
    	
    	this.setStartPage(this.getCurrentBlock());
        this.setEndPage(this.getLastBlock(), this.getCurrentBlock());

        this.setPrev();
        this.setNext();
    }
}
