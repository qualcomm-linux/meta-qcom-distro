#!/usr/bin/env python3

################################################################################
# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: BSD-3-Clause-Clear
################################################################################

import requests
import re
import sys
from urllib.parse import quote
import json

def get_github_content(owner, repo, ref, filepath):
    """Fetch content from GitHub"""
    url = f"https://api.github.com/repos/{owner}/{repo}/contents/{filepath}?ref={ref}"
    response = requests.get(url)
    if response.status_code == 200:
        content = response.json()
        # Decode base64 content
        import base64
        return base64.b64decode(content['content']).decode('utf-8')
    return None

def get_android_git_commit(repo_url, branch="main"):
    """Fetch latest commit from Android external repository"""
    try:
        # Direct API call to get latest commit
        api_url = f"{repo_url}/+/refs/heads/{branch}"
        response = requests.get(api_url)
        if response.status_code == 200:
            content = response.text
            # Look for commit hash pattern
            match = re.search(r'([a-f0-9]{40})', content)
            if match:
                return match.group(1)
    except:
        pass
    return None

def extract_from_workspace_bzl(content, pattern):
    """Extract commit from workspace.bzl content"""
    match = re.search(pattern, content)
    if match:
        return match.group(1) if match.group(1) else match.group(2)
    return None

def extract_from_cmake(content, pattern):
    """Extract commit from cmake content"""
    match = re.search(pattern, content)
    if match:
        return match.group(1) if match.group(1) else match.group(2)
    return None

def main():
    if len(sys.argv) != 2:
        print("Usage: python3 extract_tflite_srcrevs_from_github.py <tensorflow_version>")
        print("Example: python3 extract_tflite_srcrevs_from_github.py v2.20.0")
        sys.exit(1)
    
    tensorflow_version = sys.argv[1]
    owner = "tensorflow"
    repo = "tensorflow"
    
    print(f"Extracting SRCREV values for TensorFlow {tensorflow_version} from GitHub...")
    
    # Dictionary mapping component names to their extraction methods
    components = {
        'farmhash': {
            'files': [
                'third_party/xla/third_party/farmhash/workspace.bzl',
                'tensorflow/lite/tools/cmake/modules/farmhash.cmake'
            ],
            'patterns': [
                r'FARMHASH_COMMIT\s*=\s*"([^"]+)"',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'gemmlowp': {
            'files': [
                'third_party/xla/third_party/gemmlowp/workspace.bzl',
                'tensorflow/lite/tools/cmake/modules/gemmlowp.cmake'
            ],
            'patterns': [
                r'GEMMLOWP_COMMIT\s*=\s*"([^"]+)"',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'cpuinfo': {
            'files': [
                'third_party/xla/workspace2.bzl',
                'tensorflow/workspace2.bzl',
                'tensorflow/lite/tools/cmake/modules/cpuinfo.cmake'
            ],
            'patterns': [
                r'cpuinfo-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'mlDtypes': {
            'files': [
                'third_party/xla/third_party/py/ml_dtypes/workspace.bzl',
                'tensorflow/lite/tools/cmake/modules/ml_dtypes.cmake'
            ],
            'patterns': [
                r'ML_DTYPES_COMMIT\s*=\s*"([^"]+)"',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'ruy': {
            'files': [
                'third_party/ruy/workspace.bzl',
                'tensorflow/lite/tools/cmake/modules/ruy.cmake'
            ],
            'patterns': [
                r'ruy-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'openclHeaders': {
            'files': [
                'third_party/opencl_headers/workspace.bzl',
                'tensorflow/lite/tools/cmake/modules/opencl_headers.cmake'
            ],
            'patterns': [
                r'OpenCL-Headers-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'vulkanHeaders': {
            'files': [
                'third_party/vulkan_headers/workspace.bzl',
                'tensorflow/lite/tools/cmake/modules/vulkan_headers.cmake'
            ],
            'patterns': [
                r'Vulkan-Headers-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'xnnpack': {
            'files': [
                'third_party/xla/workspace2.bzl',
                'tensorflow/workspace2.bzl',
                'tensorflow/lite/tools/cmake/modules/xnnpack.cmake'
            ],
            'patterns': [
                r'XNNPACK-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'fp16': {
            'files': [
                'third_party/xla/third_party/FP16/workspace.bzl'
            ],
            'patterns': [
                r'FP16-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'kleidiai': {
            'files': [
                'third_party/xla/workspace2.bzl',
                'tensorflow/workspace2.bzl'
            ],
            'patterns': [
                r'kleidiai-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'pthreadpool': {
            'files': [
                'third_party/xla/workspace2.bzl',
                'tensorflow/workspace2.bzl',
                'tensorflow/lite/cmake/DownloadPThreadPool.cmake'
            ],
            'patterns': [
                r'pthreadpool-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'fxdiv': {
            'files': [
                'third_party/xla/workspace2.bzl',
                'tensorflow/workspace2.bzl'
            ],
            'patterns': [
                r'FXdiv-([a-f0-9]+)',
                r'GIT_TAG\s+([a-f0-9]+)'
            ]
        },
        'fft2d': {
            'special_handling': True,
            'repo_url': 'https://android.googlesource.com/platform/external/fft2d',
            'branch': 'main'
        }
    }
    
    # Extract commits
    srcrevs = {}
    
    # Handle special cases first
    if 'fft2d' in components and components['fft2d'].get('special_handling'):
        # Fetch fft2d from Android source
        try:
            # Direct API call to get latest commit
            api_url = "https://android.googlesource.com/platform/external/fft2d/+refs/heads/main"
            response = requests.get(api_url)
            if response.status_code == 200:
                content = response.text
                # Look for commit hash in the response
                match = re.search(r'([a-f0-9]{40})', content)
                if match:
                    commit = match.group(1)
                    srcrevs['fft2d'] = commit
                    print(f"Found fft2d commit: {commit}")
                else:
                    # Alternative method - try to get from a specific API endpoint
                    api_url = "https://android.googlesource.com/platform/external/fft2d/+log"
                    response = requests.get(api_url)
                    if response.status_code == 200:
                        lines = response.text.split('\n')
                        if len(lines) > 1:
                            # First line should contain the latest commit
                            first_line = lines[0]
                            match = re.search(r'^([a-f0-9]{40})', first_line)
                            if match:
                                commit = match.group(1)
                                srcrevs['fft2d'] = commit
                                print(f"Found fft2d commit: {commit}")
        except Exception as e:
            print(f"Error fetching fft2d: {e}")
            print("⚠️ Could not fetch fft2d commit, skipping...")
    
    # Handle regular components
    for component, info in components.items():
        if component == 'fft2d' or 'special_handling' in info:
            continue  # Already handled
            
        commit = None
        for file_path in info['files']:
            if commit:
                break
                
            content = get_github_content(owner, repo, tensorflow_version, file_path)
            if content:
                # Try each pattern
                for pattern in info['patterns']:
                    match = re.search(pattern, content)
                    if match:
                        # Handle capturing groups properly
                        if len(match.groups()) >= 1:
                            commit = match.group(1) if match.group(1) else match.group(2) if len(match.groups()) > 1 and match.group(2) else None
                        else:
                            commit = match.group(0)
                        if commit:
                            break
        
        if commit:
            srcrevs[component] = commit
        else:
            print(f"⚠️ Could not find commit for {component} in {tensorflow_version}")
    
    # Sort the SRCREV values alphabetically by component name
    sorted_srcrevs = dict(sorted(srcrevs.items()))
    
    # Print the results in sorted order
    print("\nGenerated SRCREV entries (sorted):")
    print("# Generated from TensorFlow GitHub repository")
    print(f"# TensorFlow version: {tensorflow_version}")
    for component, commit in sorted_srcrevs.items():
        print(f'SRCREV_{component} = "{commit}"')
    
    return sorted_srcrevs

if __name__ == "__main__":
    main()
