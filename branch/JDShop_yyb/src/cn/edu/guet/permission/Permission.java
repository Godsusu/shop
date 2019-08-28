package cn.edu.guet.permission;

public class Permission implements Comparable<Object>{
	private String permissionId;
	private String pid;
	private String name;
	private String icon;
	private String iconSkin;
	private String url;
	private String isParent;
	private String target;
	private String Checked;
	private String Open;
	
	public String getOpen() {
		return Open;
	}
	public void setOpen(String open) {
		Open = open;
	}
	public String getChecked() {
		return Checked;
	}
	public void setChecked(String checked) {
		Checked = checked;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
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
	public String getIconSkin() {
		return iconSkin;
	}
	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
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
	@Override
	public int compareTo(Object o) {
		Permission temp=(Permission)o;
		if(temp.getName().compareTo(this.name)>0){
			return 1;
		}
		else{
			return -1;
		}
	}
	
}
