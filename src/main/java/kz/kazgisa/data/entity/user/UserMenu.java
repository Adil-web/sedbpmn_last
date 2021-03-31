package kz.kazgisa.data.entity.user;

import kz.kazgisa.data.entity.base.BaseEntity;
import kz.kazgisa.data.entity.dict.Menu;
import kz.kazgisa.data.enums.MenuCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
public class UserMenu extends BaseEntity {
    private User user;
    private Menu menu;
    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
