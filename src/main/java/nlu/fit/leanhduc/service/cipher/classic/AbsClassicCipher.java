package nlu.fit.leanhduc.service.cipher.classic;

import lombok.Getter;
import nlu.fit.leanhduc.service.ICipher;
import nlu.fit.leanhduc.service.key.classic.AffineKeyClassic;
import nlu.fit.leanhduc.service.key.classic.IKeyClassic;
import nlu.fit.leanhduc.service.key.classic.SubstitutionKeyClassic;
import nlu.fit.leanhduc.util.CipherException;
import nlu.fit.leanhduc.util.FileUtil;
import nlu.fit.leanhduc.util.alphabet.AlphabetUtil;

import java.io.*;

/**
 * Class {@code AbsClassicCipher }
 * <p> Định nghĩa các method cơ bản của một thuật toán mã hóa cổ điển
 * Hiện thực hàm {@code loadKey} và {@code saveKey} của interface {@code ICipher} </p>
 *
 * @param <T> kiểu dữ liệu của khóa cổ điển
 *            <p> {@code T} cần được {@code extend} từ {@link IKeyClassic} </p>
 *            <p> Ví dụ: {@link AffineKeyClassic}, {@link SubstitutionKeyClassic} </p>
 * @author Lê Anh Đức
 * @version 1.0
 * @since 2024-11-28
 */
@Getter
public abstract class AbsClassicCipher<T extends IKeyClassic> implements ICipher<T> {
    /**
     * Khóa đại diện cho thuật toán
     */
    protected T key;

    /**
     * Đối tượng {@link AlphabetUtil} chứa bảng chữ cái để tham chiếu thực hiện mã hóa
     */
    protected AlphabetUtil alphabetUtil;

    /**
     * Constructor khởi tạo đổi tượng
     *
     * @param alphabetUtil đối tượng {@link AlphabetUtil} đại diện cho bảng chữ cái
     */
    public AbsClassicCipher(AlphabetUtil alphabetUtil) {
        this.alphabetUtil = alphabetUtil;
    }

    /**
     * Gán key cho thuật toán
     *
     * @param key khóa
     */
    @Override
    public void loadKey(T key) throws CipherException {
        this.key = key;
    }

    /**
     * Tải khóa từ src lên và gán cho thuật toán
     *
     * @param src đường dẫn file chứa khóa
     */
    @Override
    public void loadKey(String src) throws IOException {
        File file = new File(src);
//  Tiến hành đọc khóa từ file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            String name = ois.readUTF();
            IKeyClassic key = (IKeyClassic) ois.readObject();
//  Ép kiểu khóa về kiểu T
            this.loadKey((T) key);
        } catch (EOFException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lưu khá xuống file chỉ định
     *
     * @param dest đường dẫn lưu file khóa
     */
    @Override
    public void saveKey(String dest) throws IOException {
        File file = new File(dest);
//        Tạo thư mục nếu không tồn tại
        if (!file.exists()) {
            FileUtil.createPathIfNotExists(dest);
        }
//        Ghi khóa xuống file
        try (ObjectOutputStream ois = new ObjectOutputStream(new FileOutputStream(file))) {
            ois.writeUTF(key.name().getName());
            ois.writeObject(key);
        } catch (EOFException ignored) {
        }
    }
}
