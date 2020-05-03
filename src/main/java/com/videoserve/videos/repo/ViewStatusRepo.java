package com.videoserve.videos.repo;

import com.videoserve.videos.model.ViewStatus;
import com.videoserve.videos.model.ViewStatusKey;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by akshay on 03/05/20
 *
 * @author akshay
 * ViewStatusRepo
 */
public interface ViewStatusRepo extends CrudRepository<ViewStatus, ViewStatusKey> {
}
