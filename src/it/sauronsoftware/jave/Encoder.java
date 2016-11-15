package it.sauronsoftware.jave;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Encoder {
	private static final Pattern FORMAT_PATTERN = Pattern.compile("^\\s*([D ])([E ])\\s+([\\w,]+)\\s+.+$");

	private static final Pattern ENCODER_DECODER_PATTERN = Pattern
			.compile("^\\s*([D.])([E.])([AVS.])([I.])([L.])([S.])\\s+(.+?)\\s+(.+)$", 2);

	private static final Pattern PROGRESS_INFO_PATTERN = Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\S+)\\s*", 2);

	private static final Pattern SIZE_PATTERN = Pattern.compile("(\\d+)x(\\d+)", 2);

	private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("([\\d.]+)\\s+(?:fps|tb\\(r\\))", 2);

	private static final Pattern BIT_RATE_PATTERN = Pattern.compile("(\\d+)\\s+kb/s", 2);

	private static final Pattern SAMPLING_RATE_PATTERN = Pattern.compile("(\\d+)\\s+Hz", 2);

	private static final Pattern CHANNELS_PATTERN = Pattern.compile("(mono|stereo)", 2);

	private static final Pattern SUCCESS_PATTERN = Pattern.compile("^\\s*video\\:\\S+\\s+audio\\:\\S+\\s+.*$", 2);

	private static final Pattern TIME_PATTERN = Pattern.compile("^(\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d*)$", 2);
	private FFMPEGLocator locator;

	public Encoder() {
		this.locator = new DefaultFFMPEGLocator();
	}

	public Encoder(FFMPEGLocator locator) {
		this.locator = locator;
	}

	public String[] getAudioDecoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-codecs");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));

			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches())
							break;
						String decoderFlag = matcher.group(1);
						String audioVideoFlag = matcher.group(3);
						if (("D".equals(decoderFlag)) && ("A".equals(audioVideoFlag))) {
							String name = matcher.group(7);
							res.add(name);
						}

					} else if (line.trim().equals("-------")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = ((String) res.get(i));
		}
		return ret;
	}

	public String[] getAudioEncoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-codecs");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));

			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches())
							break;
						String encoderFlag = matcher.group(2);
						String audioVideoFlag = matcher.group(3);
						if (("E".equals(encoderFlag)) && ("A".equals(audioVideoFlag))) {
							String name = matcher.group(7);
							res.add(name);
						}

					} else if (line.trim().equals("-------")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = ((String) res.get(i));
		}
		return ret;
	}

	public String[] getVideoDecoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-codecs");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));

			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches())
							break;
						String decoderFlag = matcher.group(1);
						String audioVideoFlag = matcher.group(3);
						if (("D".equals(decoderFlag)) && ("V".equals(audioVideoFlag))) {
							String name = matcher.group(7);
							res.add(name);
						}

					} else if (line.trim().equals("-------")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = ((String) res.get(i));
		}
		return ret;
	}

	public String[] getVideoEncoders() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-codecs");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));

			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = ENCODER_DECODER_PATTERN.matcher(line);
						if (!matcher.matches())
							break;
						String encoderFlag = matcher.group(2);
						String audioVideoFlag = matcher.group(3);
						if (("E".equals(encoderFlag)) && ("V".equals(audioVideoFlag))) {
							String name = matcher.group(7);
							res.add(name);
						}

					} else if (line.trim().equals("-------")) {
						evaluate = true;
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = ((String) res.get(i));
		}
		return ret;
	}

	public String[] getSupportedEncodingFormats() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));

			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = FORMAT_PATTERN.matcher(line);
						if (!matcher.matches())
							break;
						String encoderFlag = matcher.group(2);
						if ("E".equals(encoderFlag)) {
							String aux = matcher.group(3);
							StringTokenizer st = new StringTokenizer(aux, ",");
							while (st.hasMoreTokens()) {
								String token = st.nextToken().trim();
								if (!res.contains(token)) {
									res.add(token);
								}
							}

						}

					} else if (line.trim().equals("File formats:")) {
						reader.readLine();
						reader.readLine();
						reader.readLine();
						evaluate = true;
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = ((String) res.get(i));
		}
		return ret;
	}

	public String[] getSupportedDecodingFormats() throws EncoderException {
		ArrayList res = new ArrayList();
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-formats");
		try {
			ffmpeg.execute();
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getInputStream()));

			boolean evaluate = false;
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() != 0) {
					if (evaluate) {
						Matcher matcher = FORMAT_PATTERN.matcher(line);
						if (!matcher.matches())
							break;
						String decoderFlag = matcher.group(1);
						if ("D".equals(decoderFlag)) {
							String aux = matcher.group(3);
							StringTokenizer st = new StringTokenizer(aux, ",");
							while (st.hasMoreTokens()) {
								String token = st.nextToken().trim();
								if (!res.contains(token)) {
									res.add(token);
								}
							}

						}

					} else if (line.trim().equals("File formats:")) {
						reader.readLine();
						reader.readLine();
						reader.readLine();
						evaluate = true;
					}
				}
			}
		} catch (IOException e) {
			throw new EncoderException(e);
		} finally {
			ffmpeg.destroy();
		}
		int size = res.size();
		String[] ret = new String[size];
		for (int i = 0; i < size; i++) {
			ret[i] = ((String) res.get(i));
		}
		return ret;
	}

	public MultimediaInfo getInfo(File source) throws InputFormatException, EncoderException {
		FFMPEGExecutor ffmpeg = this.locator.createExecutor();
		ffmpeg.addArgument("-i");
		ffmpeg.addArgument(source.getAbsolutePath());
		try {
			ffmpeg.execute();
		} catch (IOException e) {
			throw new EncoderException(e);
		}
		try {
			RBufferedReader reader = null;
			reader = new RBufferedReader(new InputStreamReader(ffmpeg.getErrorStream()));
			return parseMultimediaInfo(source, reader);
		} finally {
			ffmpeg.destroy();
		}
	}

	private MultimediaInfo parseMultimediaInfo(File source, RBufferedReader reader)
			throws InputFormatException, EncoderException {
		Pattern p1 = Pattern.compile("^\\s*Input #0, (\\w+).+$\\s*", 2);
		Pattern p2 = Pattern.compile("^\\s*Duration: (\\d\\d):(\\d\\d):(\\d\\d)\\.(\\d).*$", 2);
		Pattern p3 = Pattern.compile("^\\s*Stream #\\S+: ((?:Audio)|(?:Video)|(?:Data)): (.*)\\s*$", 2);
		MultimediaInfo info = null;
		try {
			int step = 0;
			String line;
			do {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				if (step == 0) {
					String token = source.getAbsolutePath() + ": ";
					if (line.startsWith(token)) {
						String message = line.substring(token.length());
						throw new InputFormatException(message);
					}
					Matcher m = p1.matcher(line);
					if (m.matches()) {
						String format = m.group(1);
						info = new MultimediaInfo();
						info.setFormat(format);
						step++;
					}
				} else if (step == 1) {
					Matcher m = p2.matcher(line);
					if (m.matches()) {
						long hours = Integer.parseInt(m.group(1));
						long minutes = Integer.parseInt(m.group(2));
						long seconds = Integer.parseInt(m.group(3));
						long dec = Integer.parseInt(m.group(4));
						long duration = dec * 100L + seconds * 1000L + minutes * 60L * 1000L
								+ hours * 60L * 60L * 1000L;
						info.setDuration(duration);
						step++;
					} else if (!line.startsWith("  ")) {
						step = 3;
					}
				} else if (step == 2) {
					Matcher m = p3.matcher(line);
					if (m.matches()) {
						String type = m.group(1);
						String specs = m.group(2);
						if ("Video".equalsIgnoreCase(type)) {
							VideoInfo video = new VideoInfo();
							StringTokenizer st = new StringTokenizer(specs, ",");
							for (int i = 0; st.hasMoreTokens(); i++) {
								String token = st.nextToken().trim();
								if (i == 0) {
									video.setDecoder(token);
								} else {
									boolean parsed = false;

									Matcher m2 = SIZE_PATTERN.matcher(token);
									if ((!parsed) && (m2.find())) {
										int width = Integer.parseInt(m2.group(1));
										int height = Integer.parseInt(m2.group(2));
										video.setSize(new VideoSize(width, height));
										parsed = true;
									}

									m2 = FRAME_RATE_PATTERN.matcher(token);
									if ((!parsed) && (m2.find())) {
										try {
											float frameRate = Float.parseFloat(m2.group(1));
											video.setFrameRate(frameRate);
										} catch (NumberFormatException localNumberFormatException) {
										}
										parsed = true;
									}

									m2 = BIT_RATE_PATTERN.matcher(token);
									if ((!parsed) && (m2.find())) {
										int bitRate = Integer.parseInt(m2.group(1));
										video.setBitRate(bitRate);
										parsed = true;
									}
								}
							}
							info.setVideo(video);
						} else if ("Audio".equalsIgnoreCase(type)) {
							AudioInfo audio = new AudioInfo();
							StringTokenizer st = new StringTokenizer(specs, ",");
							for (int i = 0; st.hasMoreTokens(); i++) {
								String token = st.nextToken().trim();
								if (i == 0) {
									audio.setDecoder(token);
								} else {
									boolean parsed = false;

									Matcher m2 = SAMPLING_RATE_PATTERN.matcher(token);
									if ((!parsed) && (m2.find())) {
										int samplingRate = Integer.parseInt(m2.group(1));
										audio.setSamplingRate(samplingRate);
										parsed = true;
									}

									m2 = CHANNELS_PATTERN.matcher(token);
									if ((!parsed) && (m2.find())) {
										String ms = m2.group(1);
										if ("mono".equalsIgnoreCase(ms))
											audio.setChannels(1);
										else if ("stereo".equalsIgnoreCase(ms)) {
											audio.setChannels(2);
										}
										parsed = true;
									}

									m2 = BIT_RATE_PATTERN.matcher(token);
									if ((!parsed) && (m2.find())) {
										int bitRate = Integer.parseInt(m2.group(1));
										audio.setBitRate(bitRate);
										parsed = true;
									}
								}
							}
							info.setAudio(audio);
						}
					} else if (!line.startsWith("  ")) {
						step = 3;
					}
				}
			} while (step != 3);
			reader.reinsertLine(line);
		} catch (IOException e) {
			throw new EncoderException(e);
		}
		if (info == null) {
			throw new InputFormatException();
		}
		return info;
	}

	private Hashtable parseProgressInfoLine(String line) {
		Hashtable table = null;
		Matcher m = PROGRESS_INFO_PATTERN.matcher(line);
		while (m.find()) {
			if (table == null) {
				table = new Hashtable();
			}
			String key = m.group(1);
			String value = m.group(2);
			table.put(key, value);
		}
		return table;
	}

	public void encode(File source, File target, EncodingAttributes attributes)
			throws IllegalArgumentException, InputFormatException, EncoderException {
		encode(source, target, attributes, null);
	}

	// ERROR //
	public void encode(File source, File target, EncodingAttributes attributes, EncoderProgressListener listener)
			throws IllegalArgumentException, InputFormatException, EncoderException {
		// Byte code:
		// 0: aload_3
		// 1: invokevirtual 431
		// it/sauronsoftware/jave/EncodingAttributes:getFormat
		// ()Ljava/lang/String;
		// 4: astore 5
		// 6: aload_3
		// 7: invokevirtual 436
		// it/sauronsoftware/jave/EncodingAttributes:getOffset
		// ()Ljava/lang/Float;
		// 10: astore 6
		// 12: aload_3
		// 13: invokevirtual 440
		// it/sauronsoftware/jave/EncodingAttributes:getDuration
		// ()Ljava/lang/Float;
		// 16: astore 7
		// 18: aload_3
		// 19: invokevirtual 443
		// it/sauronsoftware/jave/EncodingAttributes:getAudioAttributes
		// ()Lit/sauronsoftware/jave/AudioAttributes;
		// 22: astore 8
		// 24: aload_3
		// 25: invokevirtual 447
		// it/sauronsoftware/jave/EncodingAttributes:getVideoAttributes
		// ()Lit/sauronsoftware/jave/VideoAttributes;
		// 28: astore 9
		// 30: aload 8
		// 32: ifnonnull +19 -> 51
		// 35: aload 9
		// 37: ifnonnull +14 -> 51
		// 40: new 423 java/lang/IllegalArgumentException
		// 43: dup
		// 44: ldc_w 451
		// 47: invokespecial 453 java/lang/IllegalArgumentException:<init>
		// (Ljava/lang/String;)V
		// 50: athrow
		// 51: aload_2
		// 52: invokevirtual 454 java/io/File:getAbsoluteFile ()Ljava/io/File;
		// 55: astore_2
		// 56: aload_2
		// 57: invokevirtual 458 java/io/File:getParentFile ()Ljava/io/File;
		// 60: invokevirtual 461 java/io/File:mkdirs ()Z
		// 63: pop
		// 64: aload_0
		// 65: getfield 78 it/sauronsoftware/jave/Encoder:locator
		// Lit/sauronsoftware/jave/FFMPEGLocator;
		// 68: invokevirtual 91
		// it/sauronsoftware/jave/FFMPEGLocator:createExecutor
		// ()Lit/sauronsoftware/jave/FFMPEGExecutor;
		// 71: astore 10
		// 73: aload 6
		// 75: ifnull +24 -> 99
		// 78: aload 10
		// 80: ldc_w 464
		// 83: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 86: aload 10
		// 88: aload 6
		// 90: invokevirtual 466 java/lang/Float:floatValue ()F
		// 93: invokestatic 470 java/lang/String:valueOf (F)Ljava/lang/String;
		// 96: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 99: aload 10
		// 101: ldc 239
		// 103: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 106: aload 10
		// 108: aload_1
		// 109: invokevirtual 241 java/io/File:getAbsolutePath
		// ()Ljava/lang/String;
		// 112: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 115: aload 7
		// 117: ifnull +24 -> 141
		// 120: aload 10
		// 122: ldc_w 473
		// 125: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 128: aload 10
		// 130: aload 7
		// 132: invokevirtual 466 java/lang/Float:floatValue ()F
		// 135: invokestatic 470 java/lang/String:valueOf (F)Ljava/lang/String;
		// 138: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 141: aload 9
		// 143: ifnonnull +14 -> 157
		// 146: aload 10
		// 148: ldc_w 475
		// 151: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 154: goto +186 -> 340
		// 157: aload 9
		// 159: invokevirtual 477
		// it/sauronsoftware/jave/VideoAttributes:getCodec ()Ljava/lang/String;
		// 162: astore 11
		// 164: aload 11
		// 166: ifnull +18 -> 184
		// 169: aload 10
		// 171: ldc_w 482
		// 174: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 177: aload 10
		// 179: aload 11
		// 181: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 184: aload 9
		// 186: invokevirtual 484 it/sauronsoftware/jave/VideoAttributes:getTag
		// ()Ljava/lang/String;
		// 189: astore 12
		// 191: aload 12
		// 193: ifnull +18 -> 211
		// 196: aload 10
		// 198: ldc_w 487
		// 201: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 204: aload 10
		// 206: aload 12
		// 208: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 211: aload 9
		// 213: invokevirtual 489
		// it/sauronsoftware/jave/VideoAttributes:getBitRate
		// ()Ljava/lang/Integer;
		// 216: astore 13
		// 218: aload 13
		// 220: ifnull +24 -> 244
		// 223: aload 10
		// 225: ldc_w 493
		// 228: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 231: aload 10
		// 233: aload 13
		// 235: invokevirtual 495 java/lang/Integer:intValue ()I
		// 238: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 241: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 244: aload 9
		// 246: invokevirtual 500
		// it/sauronsoftware/jave/VideoAttributes:getFrameRate
		// ()Ljava/lang/Integer;
		// 249: astore 14
		// 251: aload 14
		// 253: ifnull +24 -> 277
		// 256: aload 10
		// 258: ldc_w 503
		// 261: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 264: aload 10
		// 266: aload 14
		// 268: invokevirtual 495 java/lang/Integer:intValue ()I
		// 271: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 274: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 277: aload 9
		// 279: invokevirtual 505 it/sauronsoftware/jave/VideoAttributes:getSize
		// ()Lit/sauronsoftware/jave/VideoSize;
		// 282: astore 15
		// 284: aload 15
		// 286: ifnull +54 -> 340
		// 289: aload 10
		// 291: ldc_w 509
		// 294: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 297: aload 10
		// 299: new 261 java/lang/StringBuilder
		// 302: dup
		// 303: aload 15
		// 305: invokevirtual 511 it/sauronsoftware/jave/VideoSize:getWidth ()I
		// 308: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 311: invokestatic 263 java/lang/String:valueOf
		// (Ljava/lang/Object;)Ljava/lang/String;
		// 314: invokespecial 267 java/lang/StringBuilder:<init>
		// (Ljava/lang/String;)V
		// 317: ldc_w 514
		// 320: invokevirtual 271 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 323: aload 15
		// 325: invokevirtual 516 it/sauronsoftware/jave/VideoSize:getHeight ()I
		// 328: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 331: invokevirtual 271 java/lang/StringBuilder:append
		// (Ljava/lang/String;)Ljava/lang/StringBuilder;
		// 334: invokevirtual 275 java/lang/StringBuilder:toString
		// ()Ljava/lang/String;
		// 337: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 340: aload 8
		// 342: ifnonnull +14 -> 356
		// 345: aload 10
		// 347: ldc_w 519
		// 350: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 353: goto +162 -> 515
		// 356: aload 8
		// 358: invokevirtual 521
		// it/sauronsoftware/jave/AudioAttributes:getCodec ()Ljava/lang/String;
		// 361: astore 11
		// 363: aload 11
		// 365: ifnull +18 -> 383
		// 368: aload 10
		// 370: ldc_w 524
		// 373: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 376: aload 10
		// 378: aload 11
		// 380: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 383: aload 8
		// 385: invokevirtual 526
		// it/sauronsoftware/jave/AudioAttributes:getBitRate
		// ()Ljava/lang/Integer;
		// 388: astore 12
		// 390: aload 12
		// 392: ifnull +24 -> 416
		// 395: aload 10
		// 397: ldc_w 527
		// 400: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 403: aload 10
		// 405: aload 12
		// 407: invokevirtual 495 java/lang/Integer:intValue ()I
		// 410: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 413: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 416: aload 8
		// 418: invokevirtual 529
		// it/sauronsoftware/jave/AudioAttributes:getChannels
		// ()Ljava/lang/Integer;
		// 421: astore 13
		// 423: aload 13
		// 425: ifnull +24 -> 449
		// 428: aload 10
		// 430: ldc_w 532
		// 433: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 436: aload 10
		// 438: aload 13
		// 440: invokevirtual 495 java/lang/Integer:intValue ()I
		// 443: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 446: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 449: aload 8
		// 451: invokevirtual 534
		// it/sauronsoftware/jave/AudioAttributes:getSamplingRate
		// ()Ljava/lang/Integer;
		// 454: astore 14
		// 456: aload 14
		// 458: ifnull +24 -> 482
		// 461: aload 10
		// 463: ldc_w 537
		// 466: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 469: aload 10
		// 471: aload 14
		// 473: invokevirtual 495 java/lang/Integer:intValue ()I
		// 476: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 479: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 482: aload 8
		// 484: invokevirtual 539
		// it/sauronsoftware/jave/AudioAttributes:getVolume
		// ()Ljava/lang/Integer;
		// 487: astore 15
		// 489: aload 15
		// 491: ifnull +24 -> 515
		// 494: aload 10
		// 496: ldc_w 542
		// 499: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 502: aload 10
		// 504: aload 15
		// 506: invokevirtual 495 java/lang/Integer:intValue ()I
		// 509: invokestatic 498 java/lang/String:valueOf (I)Ljava/lang/String;
		// 512: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 515: aload 10
		// 517: ldc_w 544
		// 520: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 523: aload 10
		// 525: aload 5
		// 527: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 530: aload 10
		// 532: ldc_w 546
		// 535: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 538: aload 10
		// 540: aload_2
		// 541: invokevirtual 241 java/io/File:getAbsolutePath
		// ()Ljava/lang/String;
		// 544: invokevirtual 99
		// it/sauronsoftware/jave/FFMPEGExecutor:addArgument
		// (Ljava/lang/String;)V
		// 547: aload 10
		// 549: invokevirtual 105 it/sauronsoftware/jave/FFMPEGExecutor:execute
		// ()V
		// 552: goto +15 -> 567
		// 555: astore 11
		// 557: new 86 it/sauronsoftware/jave/EncoderException
		// 560: dup
		// 561: aload 11
		// 563: invokespecial 162 it/sauronsoftware/jave/EncoderException:<init>
		// (Ljava/lang/Throwable;)V
		// 566: athrow
		// 567: aconst_null
		// 568: astore 11
		// 570: lconst_0
		// 571: lstore 14
		// 573: aconst_null
		// 574: astore 16
		// 576: new 108 it/sauronsoftware/jave/RBufferedReader
		// 579: dup
		// 580: new 110 java/io/InputStreamReader
		// 583: dup
		// 584: aload 10
		// 586: invokevirtual 246
		// it/sauronsoftware/jave/FFMPEGExecutor:getErrorStream
		// ()Ljava/io/InputStream;
		// 589: invokespecial 116 java/io/InputStreamReader:<init>
		// (Ljava/io/InputStream;)V
		// 592: invokespecial 119 it/sauronsoftware/jave/RBufferedReader:<init>
		// (Ljava/io/Reader;)V
		// 595: astore 16
		// 597: aload_0
		// 598: aload_1
		// 599: aload 16
		// 601: invokespecial 249
		// it/sauronsoftware/jave/Encoder:parseMultimediaInfo
		// (Ljava/io/File;Lit/sauronsoftware/jave/RBufferedReader;)Lit/sauronsoftware/jave/MultimediaInfo;
		// 604: astore 17
		// 606: aload 7
		// 608: ifnull +21 -> 629
		// 611: aload 7
		// 613: invokevirtual 466 java/lang/Float:floatValue ()F
		// 616: ldc_w 548
		// 619: fmul
		// 620: invokestatic 549 java/lang/Math:round (F)I
		// 623: i2l
		// 624: lstore 12
		// 626: goto +33 -> 659
		// 629: aload 17
		// 631: invokevirtual 555
		// it/sauronsoftware/jave/MultimediaInfo:getDuration ()J
		// 634: lstore 12
		// 636: aload 6
		// 638: ifnull +21 -> 659
		// 641: lload 12
		// 643: aload 6
		// 645: invokevirtual 466 java/lang/Float:floatValue ()F
		// 648: ldc_w 548
		// 651: fmul
		// 652: invokestatic 549 java/lang/Math:round (F)I
		// 655: i2l
		// 656: lsub
		// 657: lstore 12
		// 659: aload 4
		// 661: ifnull +12 -> 673
		// 664: aload 4
		// 666: aload 17
		// 668: invokeinterface 558 2 0
		// 673: iconst_0
		// 674: istore 18
		// 676: goto +404 -> 1080
		// 679: iload 18
		// 681: ifne +80 -> 761
		// 684: aload 19
		// 686: ldc_w 564
		// 689: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 692: ifeq +20 -> 712
		// 695: aload 4
		// 697: ifnull +84 -> 781
		// 700: aload 4
		// 702: aload 19
		// 704: invokeinterface 566 2 0
		// 709: goto +72 -> 781
		// 712: aload 19
		// 714: ldc_w 568
		// 717: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 720: ifeq +9 -> 729
		// 723: iinc 18 1
		// 726: goto +55 -> 781
		// 729: aload 19
		// 731: ldc_w 308
		// 734: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 737: ifne +44 -> 781
		// 740: aload 19
		// 742: ldc_w 570
		// 745: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 748: ifne +33 -> 781
		// 751: new 86 it/sauronsoftware/jave/EncoderException
		// 754: dup
		// 755: aload 19
		// 757: invokespecial 572 it/sauronsoftware/jave/EncoderException:<init>
		// (Ljava/lang/String;)V
		// 760: athrow
		// 761: iload 18
		// 763: iconst_1
		// 764: if_icmpne +17 -> 781
		// 767: aload 19
		// 769: ldc_w 308
		// 772: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 775: ifne +6 -> 781
		// 778: iinc 18 1
		// 781: iload 18
		// 783: iconst_2
		// 784: if_icmpne +30 -> 814
		// 787: aload 19
		// 789: ldc_w 573
		// 792: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 795: ifne +13 -> 808
		// 798: new 86 it/sauronsoftware/jave/EncoderException
		// 801: dup
		// 802: aload 19
		// 804: invokespecial 572 it/sauronsoftware/jave/EncoderException:<init>
		// (Ljava/lang/String;)V
		// 807: athrow
		// 808: iinc 18 1
		// 811: goto +23 -> 834
		// 814: iload 18
		// 816: iconst_3
		// 817: if_icmpne +17 -> 834
		// 820: aload 19
		// 822: ldc_w 308
		// 825: invokevirtual 278 java/lang/String:startsWith
		// (Ljava/lang/String;)Z
		// 828: ifne +6 -> 834
		// 831: iinc 18 1
		// 834: iload 18
		// 836: iconst_4
		// 837: if_icmpne +243 -> 1080
		// 840: aload 19
		// 842: invokevirtual 122 java/lang/String:trim ()Ljava/lang/String;
		// 845: astore 19
		// 847: aload 19
		// 849: invokevirtual 128 java/lang/String:length ()I
		// 852: ifle +228 -> 1080
		// 855: aload_0
		// 856: aload 19
		// 858: invokespecial 575
		// it/sauronsoftware/jave/Encoder:parseProgressInfoLine
		// (Ljava/lang/String;)Ljava/util/Hashtable;
		// 861: astore 20
		// 863: aload 20
		// 865: ifnonnull +38 -> 903
		// 868: aload 4
		// 870: ifnull +12 -> 882
		// 873: aload 4
		// 875: aload 19
		// 877: invokeinterface 566 2 0
		// 882: aload 19
		// 884: astore 11
		// 886: getstatic 64 it/sauronsoftware/jave/Encoder:SUCCESS_PATTERN
		// Ljava/util/regex/Pattern;
		// 889: aload 11
		// 891: invokevirtual 132 java/util/regex/Pattern:matcher
		// (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
		// 894: invokevirtual 136 java/util/regex/Matcher:matches ()Z
		// 897: ifeq +183 -> 1080
		// 900: goto +191 -> 1091
		// 903: aload 4
		// 905: ifnull +172 -> 1077
		// 908: aload 20
		// 910: ldc_w 577
		// 913: invokevirtual 579 java/util/Hashtable:get
		// (Ljava/lang/Object;)Ljava/lang/Object;
		// 916: checkcast 123 java/lang/String
		// 919: astore 21
		// 921: aload 21
		// 923: ifnull +154 -> 1077
		// 926: getstatic 68 it/sauronsoftware/jave/Encoder:TIME_PATTERN
		// Ljava/util/regex/Pattern;
		// 929: aload 21
		// 931: invokevirtual 132 java/util/regex/Pattern:matcher
		// (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
		// 934: astore 22
		// 936: aload 22
		// 938: invokevirtual 136 java/util/regex/Matcher:matches ()Z
		// 941: ifeq +136 -> 1077
		// 944: aload 22
		// 946: iconst_1
		// 947: invokevirtual 142 java/util/regex/Matcher:group
		// (I)Ljava/lang/String;
		// 950: invokestatic 292 java/lang/Integer:parseInt
		// (Ljava/lang/String;)I
		// 953: i2l
		// 954: lstore 23
		// 956: aload 22
		// 958: iconst_2
		// 959: invokevirtual 142 java/util/regex/Matcher:group
		// (I)Ljava/lang/String;
		// 962: invokestatic 292 java/lang/Integer:parseInt
		// (Ljava/lang/String;)I
		// 965: i2l
		// 966: lstore 25
		// 968: aload 22
		// 970: iconst_3
		// 971: invokevirtual 142 java/util/regex/Matcher:group
		// (I)Ljava/lang/String;
		// 974: invokestatic 292 java/lang/Integer:parseInt
		// (Ljava/lang/String;)I
		// 977: i2l
		// 978: lstore 27
		// 980: aload 22
		// 982: iconst_4
		// 983: invokevirtual 142 java/util/regex/Matcher:group
		// (I)Ljava/lang/String;
		// 986: invokestatic 292 java/lang/Integer:parseInt
		// (Ljava/lang/String;)I
		// 989: i2l
		// 990: lstore 29
		// 992: lload 29
		// 994: ldc2_w 298
		// 997: lmul
		// 998: lload 27
		// 1000: ldc2_w 300
		// 1003: lmul
		// 1004: ladd
		// 1005: lload 25
		// 1007: ldc2_w 302
		// 1010: lmul
		// 1011: ldc2_w 300
		// 1014: lmul
		// 1015: ladd
		// 1016: lload 23
		// 1018: ldc2_w 302
		// 1021: lmul
		// 1022: ldc2_w 302
		// 1025: lmul
		// 1026: ldc2_w 300
		// 1029: lmul
		// 1030: ladd
		// 1031: lstore 14
		// 1033: lload 14
		// 1035: ldc2_w 300
		// 1038: lmul
		// 1039: l2d
		// 1040: lload 12
		// 1042: l2d
		// 1043: ddiv
		// 1044: invokestatic 582 java/lang/Math:round (D)J
		// 1047: l2i
		// 1048: istore 31
		// 1050: iload 31
		// 1052: sipush 1000
		// 1055: if_icmple +8 -> 1063
		// 1058: sipush 1000
		// 1061: istore 31
		// 1063: aload 4
		// 1065: iload 31
		// 1067: invokeinterface 585 2 0
		// 1072: goto +5 -> 1077
		// 1075: astore 23
		// 1077: aconst_null
		// 1078: astore 11
		// 1080: aload 16
		// 1082: invokevirtual 159
		// it/sauronsoftware/jave/RBufferedReader:readLine ()Ljava/lang/String;
		// 1085: dup
		// 1086: astore 19
		// 1088: ifnonnull -409 -> 679
		// 1091: aload 11
		// 1093: ifnull +49 -> 1142
		// 1096: getstatic 64 it/sauronsoftware/jave/Encoder:SUCCESS_PATTERN
		// Ljava/util/regex/Pattern;
		// 1099: aload 11
		// 1101: invokevirtual 132 java/util/regex/Pattern:matcher
		// (Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
		// 1104: invokevirtual 136 java/util/regex/Matcher:matches ()Z
		// 1107: ifne +35 -> 1142
		// 1110: new 86 it/sauronsoftware/jave/EncoderException
		// 1113: dup
		// 1114: aload 11
		// 1116: invokespecial 572
		// it/sauronsoftware/jave/EncoderException:<init> (Ljava/lang/String;)V
		// 1119: athrow
		// 1120: astore 11
		// 1122: new 86 it/sauronsoftware/jave/EncoderException
		// 1125: dup
		// 1126: aload 11
		// 1128: invokespecial 162
		// it/sauronsoftware/jave/EncoderException:<init>
		// (Ljava/lang/Throwable;)V
		// 1131: athrow
		// 1132: astore 32
		// 1134: aload 10
		// 1136: invokevirtual 165 it/sauronsoftware/jave/FFMPEGExecutor:destroy
		// ()V
		// 1139: aload 32
		// 1141: athrow
		// 1142: aload 10
		// 1144: invokevirtual 165 it/sauronsoftware/jave/FFMPEGExecutor:destroy
		// ()V
		// 1147: return
		//
		// Exception table:
		// from to target type
		// 547 552 555 java/io/IOException
		// 944 1072 1075 java/lang/NumberFormatException
		// 567 1120 1120 java/io/IOException
		// 567 1132 1132 finally
	}
}