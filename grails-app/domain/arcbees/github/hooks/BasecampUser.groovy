package arcbees.github.hooks

class BasecampUser {

    Long personId

    String name

    static hasMany = [gitUsers: GitUser]

//    static constraints = {
//    	id visible:false
//	}

    static mapping = {
        gitUsers fetch: 'join'
    }

    String toString() { name }

}
