package jy.project.review.page;

public class pageMaker {
    private int totalCount;
    private int countList;
    private int pageNum; 
//    한 페이지에 나올 수 있는 컨텐츠의 수
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
//    	현재 페이지가 1페이지라면 이전 페이지는 존재하지 않는다.
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
 
//    단순히 다음 페이지를 의미하는 것이 아니라 ">" 이런 식의 다음 화살표를 뜻한다.
//    페이지 블록이 5개 이상이면 화살표가 생긴다.
    public void setNext() {
//    	새로운 페이지가 생성되는 조건으로, 현재 ContentNum이 10인 경우
//    	totalCount가 45라면 10 * 5가 된다. 즉 6페이지로 넘어가지 않고 5페이지의 5개의 컨텐츠가 채워질 것이다.
//    	즉, 화살표는 생기지 않는다.
    	if (this.totalCount < range * this.contentNum) {
      		this.next = false;
      	}
//    	현재 마지막 페이지 블록이라면 next는 없다
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
 
//    현재 페이지의 시작 부분 가져오기
//    즉, currentBlock이 3이라면 (3 * 5) - (5 - 1) = 11이 된다.
    public void setStartPage(int currentBlock) {
        this.startPage = (currentBlock * range) - (range - 1);
    }
 
    public int getEndPage() {
        return endPage;
    }
 
//    마지막 페이지 설정
//    즉, 어떻게 데이터를 채울지에 대한 방법
    public void setEndPage(int getlastblock,int getcurrentblock) {
//    	현재 블록이 마지막 블록이라면
        if (getlastblock==getcurrentblock) {
            this.endPage = calcPage(getTotalCount(),getContentNum());
        }
        else {
//        	시작 페이지를 이용해서 마지막 페이지 결정
            this.endPage = getStartPage() + (range - 1);
        }
    }
    
    public int getCurrentBlock() {
        return currentBlock;
    }
 
//    현재 블록을 가져오기 위해서는 페이지 번호를 range만큼 나눠야 함
//    페이지네이션의 번호라고 생각하면 쉬움
    public void setCurrentBlock(int pageNum) {
        this.currentBlock = pageNum / range;
//        7개의 컨텐츠인 경우 페이지 블록이 딱 나누어 떨어지지 않으므로
        if (pageNum % range > 0) {
            this.currentBlock++;
        }
    }
 
    public int getLastBlock() {
        return lastBlock;
    }
 
//    마지막 페이지 블록 설정하기
    public void setLastBlock(int totalCount) {
    	this.lastBlock = totalCount / (range * this.contentNum);
        if (totalCount % (range * this.contentNum) > 0) {
            this.lastBlock++;
        }
    }
  
//    페이지 계산하기
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
//    	페이지는 0부터 시작하므로
    	this.setPageNum(pageNum - 1);
        this.setContentNum(contentNum);
//        페이지를 어떻게 구성할 건지에 대한 것
        this.setStartList((pageNum - 1) * contentNum); 
    	
    	this.setStartPage(this.getCurrentBlock());
        this.setEndPage(this.getLastBlock(), this.getCurrentBlock());

        this.setPrev();
        this.setNext();
    }
}
