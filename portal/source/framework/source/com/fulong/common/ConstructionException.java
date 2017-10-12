package com.fulong.common;

/**
 * <p>Title: LongCon WebMaster</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: Copyright (c) 中科辅龙计算机技术有限公司 2005</p>
 *
 * <p>Company: 中科辅龙计算机技术有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public class ConstructionException
    extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = -972979951930309365L;

public ConstructionException() {
    super();
  }

  public ConstructionException(String message) {
    super(message);
  }

  public ConstructionException(Throwable cause) {
    super(cause);
  }

  public ConstructionException(String message, Throwable cause) {
    super(message, cause);
  }
}
