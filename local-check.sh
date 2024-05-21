#
# Copyright 2024 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
#

set -e

log() {
  echo "\033[0;32m> $1\033[0m"
}

./gradlew clean compileKotlinIosX64
log "gradlew compileKotlinIosX64 success"

./gradlew clean podspec build generateDummyFramework
log "gradlew build success"

(
cd iosApp &&
pod install &&
set -o pipefail &&
xcodebuild -scheme iosApp -workspace iosApp.xcworkspace \
  -configuration Debug \
  -destination "generic/platform=iOS Simulator" \
  build \
  CODE_SIGNING_REQUIRED=NO \
  CODE_SIGNING_ALLOWED=NO
)
log "xcodebuild success"
