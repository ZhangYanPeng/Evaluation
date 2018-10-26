package cn.edu.xjtu.evaluation.service;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.xjtu.evaluation.entity.Audio;

public interface IAudioService {
	int save(Audio audio, MultipartFile file);
	int delete(long id);
	Audio getByQue(long id);
	Audio getByIn(long id);
	int removeAud(Long aud_id);
}
