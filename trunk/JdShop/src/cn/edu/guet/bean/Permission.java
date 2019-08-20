package cn.edu.guet.bean;

public class Permission implements Comparable{

	private String permissionid;
	private String pid;
	private String name;
	private String icon;
	private String iconSkin;
	private String url;
	private String isParent;
	private String target;
	private String checked;//标记权限是否选中
	private String open;
	
	
	public String getPermissionid() {
		return permissionid;
	}
	public void setPermissionid(String permissionid) {
		this.permissionid = permissionid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}
	@Override
	public int compareTo(Object o) {
		Permission temp=(Permission)o;
		/**
		 * 按照权限的名字进行排序
		 */
		if(temp.getName().compareTo(this.getName())>0){
			return -1;
		}
		else{
			return 1;
		}
	}
}