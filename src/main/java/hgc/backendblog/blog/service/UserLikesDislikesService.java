package hgc.backendblog.blog.service;

public interface UserLikesDislikesService {

    /**
     * Añade un like al blog especificado por el usuario dado.
     * 
     * @param username El nombre de usuario del usuario que da el like.
     * @param blogId   El ID del blog al que se le da like.
     * @return true si se añadió el like correctamente, false si ocurrió un error.
     */
    boolean likeBlog(String username, String blogId);

    /**
     * Quita un like del blog especificado por el usuario dado.
     * 
     * @param username El nombre de usuario del usuario que quita el like.
     * @param blogId   El ID del blog del que se quita el like.
     * @return true si se quitó el like correctamente, false si ocurrió un error.
     */
    boolean unlikeBlog(String username, String blogId);

    /**
     * Añade un dislike al blog especificado por el usuario dado.
     * 
     * @param username El nombre de usuario del usuario que da el dislike.
     * @param blogId   El ID del blog al que se le da dislike.
     * @return true si se añadió el dislike correctamente, false si ocurrió un error.
     */
    boolean dislikeBlog(String username, String blogId);

    /**
     * Quita un dislike del blog especificado por el usuario dado.
     * 
     * @param username El nombre de usuario del usuario que quita el dislike.
     * @param blogId   El ID del blog del que se quita el dislike.
     * @return true si se quitó el dislike correctamente, false si ocurrió un error.
     */
    boolean undislikeBlog(String username, String blogId);
}
