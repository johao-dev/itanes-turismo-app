package com.senati.itanesturismo.data.local;

import com.senati.itanesturismo.data.model.Favorite;
import com.senati.itanesturismo.data.model.TouristPoint;

import java.util.List;

public class LocalDataSource {

    private final TouristPointDAO touristPointDAO;
    private final FavoriteDAO favoriteDAO;

    public LocalDataSource(AppDatabase db) {
        this.touristPointDAO = db.touristPointDAO();
        this.favoriteDAO = db.favoriteDAO();
    }

    public List<TouristPoint> getTouristPoints() {
        return touristPointDAO.findAll();
    }

    public TouristPoint getTouristPointById(int id) {
        return touristPointDAO.findById(id);
    }

    public List<TouristPoint> getFavoritesByUserId(int userId) {
        return favoriteDAO.findAllFavoritesByUserId(userId);
    }

    // Estos métodos sirve para sincronizar los datos de la API con la base de datos local
    public void saveTouristPoints(List<TouristPoint> touristPoints) {
        touristPointDAO.insertAll(touristPoints);
    }
    public void saveTouristPoint(TouristPoint touristPoint) {
        touristPointDAO.update(touristPoint);
    }

    public void addFavorite(Favorite favorite) {
        favoriteDAO.insert(favorite);
    }

    public void removeFavorite(int userId, int touristPointId) {
        favoriteDAO.deleteByIds(userId, touristPointId);
    }
}
